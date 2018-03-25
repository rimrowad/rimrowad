import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RimrowadSharedModule } from '../../shared';
import {
    MembreEquipeService,
    MembreEquipePopupService,
    MembreEquipeComponent,
    MembreEquipeDetailComponent,
    MembreEquipeDialogComponent,
    MembreEquipePopupComponent,
    MembreEquipeDeletePopupComponent,
    MembreEquipeDeleteDialogComponent,
    membreEquipeRoute,
    membreEquipePopupRoute,
} from './';

const ENTITY_STATES = [
    ...membreEquipeRoute,
    ...membreEquipePopupRoute,
];

@NgModule({
    imports: [
        RimrowadSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MembreEquipeComponent,
        MembreEquipeDetailComponent,
        MembreEquipeDialogComponent,
        MembreEquipeDeleteDialogComponent,
        MembreEquipePopupComponent,
        MembreEquipeDeletePopupComponent,
    ],
    entryComponents: [
        MembreEquipeComponent,
        MembreEquipeDialogComponent,
        MembreEquipePopupComponent,
        MembreEquipeDeleteDialogComponent,
        MembreEquipeDeletePopupComponent,
    ],
    providers: [
        MembreEquipeService,
        MembreEquipePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RimrowadMembreEquipeModule {}
