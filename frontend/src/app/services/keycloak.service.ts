import Keycloak from 'keycloak-js';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class KeycloakService {
    private keycloak!: Keycloak;

    async init(): Promise<void> {
        this.keycloak = new Keycloak({
            url: 'http://localhost:8081',
            realm: 'oprosita',
            clientId: 'angular-app'
        });

        await this.keycloak.init({
            onLoad: 'login-required',
            checkLoginIframe: false,
            pkceMethod: 'S256'
        });
    }

    get token(): string {
        return this.keycloak.token!;
    }

    hasRole(role: string): boolean {
        return this.keycloak.tokenParsed?.realm_access?.roles.includes(role) || false;
    }

    logout(): void {
        this.keycloak.logout({ redirectUri: window.location.origin });
    }
}
