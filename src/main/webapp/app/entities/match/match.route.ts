import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MatchComponent } from './match.component';
import { MatchDetailComponent } from './match-detail.component';
import { MatchPopupComponent } from './match-dialog.component';
import { MatchDeletePopupComponent } from './match-delete-dialog.component';

@Injectable()
export class MatchResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const matchRoute: Routes = [
    {
        path: 'match',
        component: MatchComponent,
        resolve: {
            'pagingParams': MatchResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.match.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'match/:id',
        component: MatchDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.match.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matchPopupRoute: Routes = [
    {
        path: 'match-new',
        component: MatchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.match.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match/:id/edit',
        component: MatchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.match.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match/:id/delete',
        component: MatchDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.match.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
