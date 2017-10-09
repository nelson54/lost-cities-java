import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    CommandEntityService,
    CommandEntityPopupService,
    CommandEntityComponent,
    CommandEntityDetailComponent,
    CommandEntityDialogComponent,
    CommandEntityPopupComponent,
    CommandEntityDeletePopupComponent,
    CommandEntityDeleteDialogComponent,
    commandEntityRoute,
    commandEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...commandEntityRoute,
    ...commandEntityPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CommandEntityComponent,
        CommandEntityDetailComponent,
        CommandEntityDialogComponent,
        CommandEntityDeleteDialogComponent,
        CommandEntityPopupComponent,
        CommandEntityDeletePopupComponent,
    ],
    entryComponents: [
        CommandEntityComponent,
        CommandEntityDialogComponent,
        CommandEntityPopupComponent,
        CommandEntityDeleteDialogComponent,
        CommandEntityDeletePopupComponent,
    ],
    providers: [
        CommandEntityService,
        CommandEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterCommandEntityModule {}
