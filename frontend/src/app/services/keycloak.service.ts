import Keycloak from 'keycloak-js';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class KeycloakService {
  private keycloak!: Keycloak;
  async init(): Promise<void> {
    const storedToken = localStorage.getItem('kc_token');
    const storedRefreshToken = localStorage.getItem('kc_refreshToken');

    this.keycloak = new Keycloak({
      url: 'http://localhost:8081',
      realm: 'oprosita',
      clientId: 'angular-app'
    });

    await this.keycloak.init({
      onLoad: 'check-sso',
      pkceMethod: 'S256',
      checkLoginIframe: false,
      scope: 'openid offline_access',
      token: storedToken || undefined,
      refreshToken: storedRefreshToken || undefined
    });

    if (!this.keycloak.authenticated) {
      try {
        await this.keycloak.login({ redirectUri: window.location.origin });
      } catch (err) {
        console.error('Login failed', err);
      }
    } else {
      localStorage.setItem('kc_token', this.keycloak.token!);
      localStorage.setItem('kc_refreshToken', this.keycloak.refreshToken!);
    }

    this.keycloak.onTokenExpired = () => {
      this.keycloak.updateToken(30).then(() => {
        localStorage.setItem('kc_token', this.keycloak.token!);
        localStorage.setItem('kc_refreshToken', this.keycloak.refreshToken!);
      }).catch(() => this.logout());
    };

    setInterval(() => {
      this.keycloak.updateToken(60).then(refreshed => {
        if (refreshed) {
          localStorage.setItem('kc_token', this.keycloak.token!);
          localStorage.setItem('kc_refreshToken', this.keycloak.refreshToken!);
        }
      }).catch(() => this.logout());
    }, 300000);
  }


  get token(): string {
    return this.keycloak.token!;
  }

  hasRole(role: string): boolean {
    return this.keycloak.tokenParsed?.realm_access?.roles.includes(role) || false;
  }

  logout(): void {
    localStorage.removeItem('kc_token');
    localStorage.removeItem('kc_refreshToken');
    this.keycloak.logout({ redirectUri: window.location.origin });
  }
}
