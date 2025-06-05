import {Component, OnInit, signal} from '@angular/core';
import {
  ActivatedRoute,
  NavigationEnd,
  Router,
  RouterLink
} from '@angular/router';
import { filter } from 'rxjs/operators';
import { MatIcon } from '@angular/material/icon';
import { UserProfileComponent } from '../user-profile/user-profile.component';
import {MatAnchor, MatButton} from '@angular/material/button';
import { CommonModule } from '@angular/common';
import {SendbirdWidgetComponent} from '../sendbird-widget/sendbird-widget.component';

interface Breadcrumb {
  label: string;
  url: string;
}

@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  imports: [
    CommonModule,
    MatIcon,
    UserProfileComponent,
    RouterLink,
    MatAnchor,
    SendbirdWidgetComponent,
    MatButton
  ],
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.css'
})
export class BreadcrumbComponent implements OnInit {
  breadcrumbs: Breadcrumb[] = [];

  constructor(private router: Router, private route: ActivatedRoute) {}
  chatVisible = signal(false);

  toggleChat(): void {
    this.chatVisible.set(!this.chatVisible());
  }

  ngOnInit(): void {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.breadcrumbs = this.buildBreadcrumbs(this.route.root);
      });
  }

  private buildBreadcrumbs(
    route: ActivatedRoute,
    url: string = '',
    breadcrumbs: Breadcrumb[] = []
  ): Breadcrumb[] {
    const children: ActivatedRoute[] = route.children;

    for (const child of children) {
      const routeConfig = child.routeConfig;

      if (!routeConfig) continue;

      let path = routeConfig.path ?? '';
      const params = child.snapshot.params;

      // Sustituir los :param por valores reales
      Object.keys(params).forEach(key => {
        path = path.replace(`:${key}`, params[key]);
      });

      url += `/${path}`;

      // Dividir la path si incluye múltiples segmentos como 'grupos/grupo-1'
      const segments = path.split('/');
      for (const segment of segments) {
        const segmentUrl = `${url.split('/').slice(0, breadcrumbs.length + 2).join('/')}`;
        breadcrumbs.push({
          label: this.formatLabel(segment),
          url: segmentUrl
        });
      }

      return this.buildBreadcrumbs(child, url, breadcrumbs);
    }

    return breadcrumbs;
  }

  private formatLabel(str: string): string {
    return str
      .replace(/-/g, ' ')
      .replace(/\b\w/g, l => l.toUpperCase());
  }
}
