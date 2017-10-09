import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import { JhipsterAdminModule } from '../../admin/admin.module';
import {
    GameUserService,
    GameUserPopupService,
    GameUserComponent,
    GameUserDetailComponent,
    GameUserDialogComponent,
    GameUserPopupComponent,
    GameUserDeletePopupComponent,
    GameUserDeleteDialogComponent,
    gameUserRoute,
    gameUserPopupRoute,
} from './';

const ENTITY_STATES = [
    ...gameUserRoute,
    ...gameUserPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        JhipsterAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GameUserComponent,
        GameUserDetailComponent,
        GameUserDialogComponent,
        GameUserDeleteDialogComponent,
        GameUserPopupComponent,
        GameUserDeletePopupComponent,
    ],
    entryComponents: [
        GameUserComponent,
        GameUserDialogComponent,
        GameUserPopupComponent,
        GameUserDeleteDialogComponent,
        GameUserDeletePopupComponent,
    ],
    providers: [
        GameUserService,
        GameUserPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterGameUserModule {}
