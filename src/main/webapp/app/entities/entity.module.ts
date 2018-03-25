import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RimrowadEquipeModule } from './equipe/equipe.module';
import { RimrowadMembreEquipeModule } from './membre-equipe/membre-equipe.module';
import { RimrowadProjetModule } from './projet/projet.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        RimrowadEquipeModule,
        RimrowadMembreEquipeModule,
        RimrowadProjetModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RimrowadEntityModule {}
