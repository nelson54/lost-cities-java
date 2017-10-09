import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CommandEntityComponent } from './command-entity.component';
import { CommandEntityDetailComponent } from './command-entity-detail.component';
import { CommandEntityPopupComponent } from './command-entity-dialog.component';
import { CommandEntityDeletePopupComponent } from './command-entity-delete-dialog.component';

export const commandEntityRoute: Routes = [
    {
        path: 'command-entity',
        component: CommandEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.commandEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'command-entity/:id',
        component: CommandEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.commandEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commandEntityPopupRoute: Routes = [
    {
        path: 'command-entity-new',
        component: CommandEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.commandEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'command-entity/:id/edit',
        component: CommandEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.commandEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'command-entity/:id/delete',
        component: CommandEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.commandEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
