import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RimrowadSharedModule } from '../../shared';
import { RimrowadAdminModule } from '../../admin/admin.module';
import {
    ProjetService,
    ProjetPopupService,
    ProjetComponent,
    ProjetDetailComponent,
    ProjetDialogComponent,
    ProjetPopupComponent,
    ProjetDeletePopupComponent,
    ProjetDeleteDialogComponent,
    projetRoute,
    projetPopupRoute,
} from './';

const ENTITY_STATES = [
    ...projetRoute,
    ...projetPopupRoute,
];

@NgModule({
    imports: [
        RimrowadSharedModule,
        RimrowadAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProjetComponent,
        ProjetDetailComponent,
        ProjetDialogComponent,
        ProjetDeleteDialogComponent,
        ProjetPopupComponent,
        ProjetDeletePopupComponent,
    ],
    entryComponents: [
        ProjetComponent,
        ProjetDialogComponent,
        ProjetPopupComponent,
        ProjetDeleteDialogComponent,
        ProjetDeletePopupComponent,
    ],
    providers: [
        ProjetService,
        ProjetPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RimrowadProjetModule {}
