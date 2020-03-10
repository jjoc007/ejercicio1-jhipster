import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransmition, Transmition } from 'app/shared/model/transmition.model';
import { TransmitionService } from './transmition.service';
import { TransmitionComponent } from './transmition.component';
import { TransmitionDetailComponent } from './transmition-detail.component';
import { TransmitionUpdateComponent } from './transmition-update.component';

@Injectable({ providedIn: 'root' })
export class TransmitionResolve implements Resolve<ITransmition> {
  constructor(private service: TransmitionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransmition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transmition: HttpResponse<Transmition>) => {
          if (transmition.body) {
            return of(transmition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Transmition());
  }
}

export const transmitionRoute: Routes = [
  {
    path: '',
    component: TransmitionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'broadcastApp.transmition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransmitionDetailComponent,
    resolve: {
      transmition: TransmitionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'broadcastApp.transmition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransmitionUpdateComponent,
    resolve: {
      transmition: TransmitionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'broadcastApp.transmition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransmitionUpdateComponent,
    resolve: {
      transmition: TransmitionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'broadcastApp.transmition.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
