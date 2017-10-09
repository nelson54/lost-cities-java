import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    MatchService,
    MatchPopupService,
    MatchComponent,
    MatchDetailComponent,
    MatchDialogComponent,
    MatchPopupComponent,
    MatchDeletePopupComponent,
    MatchDeleteDialogComponent,
    matchRoute,
    matchPopupRoute,
    MatchResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...matchRoute,
    ...matchPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MatchComponent,
        MatchDetailComponent,
        MatchDialogComponent,
        MatchDeleteDialogComponent,
        MatchPopupComponent,
        MatchDeletePopupComponent,
    ],
    entryComponents: [
        MatchComponent,
        MatchDialogComponent,
        MatchPopupComponent,
        MatchDeleteDialogComponent,
        MatchDeletePopupComponent,
    ],
    providers: [
        MatchService,
        MatchPopupService,
        MatchResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterMatchModule {}
