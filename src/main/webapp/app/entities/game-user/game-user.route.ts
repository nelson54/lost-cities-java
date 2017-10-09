import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GameUserComponent } from './game-user.component';
import { GameUserDetailComponent } from './game-user-detail.component';
import { GameUserPopupComponent } from './game-user-dialog.component';
import { GameUserDeletePopupComponent } from './game-user-delete-dialog.component';

export const gameUserRoute: Routes = [
    {
        path: 'game-user',
        component: GameUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.gameUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'game-user/:id',
        component: GameUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.gameUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gameUserPopupRoute: Routes = [
    {
        path: 'game-user-new',
        component: GameUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.gameUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'game-user/:id/edit',
        component: GameUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.gameUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'game-user/:id/delete',
        component: GameUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.gameUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
