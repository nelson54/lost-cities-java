import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterMatchModule } from './match/match.module';
import { JhipsterCommandEntityModule } from './command-entity/command-entity.module';
import { JhipsterGameUserModule } from './game-user/game-user.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterMatchModule,
        JhipsterCommandEntityModule,
        JhipsterGameUserModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterEntityModule {}
