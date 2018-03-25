import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MembreEquipeComponent } from './membre-equipe.component';
import { MembreEquipeDetailComponent } from './membre-equipe-detail.component';
import { MembreEquipePopupComponent } from './membre-equipe-dialog.component';
import { MembreEquipeDeletePopupComponent } from './membre-equipe-delete-dialog.component';

export const membreEquipeRoute: Routes = [
    {
        path: 'membre-equipe',
        component: MembreEquipeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimrowadApp.membreEquipe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'membre-equipe/:id',
        component: MembreEquipeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimrowadApp.membreEquipe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const membreEquipePopupRoute: Routes = [
    {
        path: 'membre-equipe-new',
        component: MembreEquipePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimrowadApp.membreEquipe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'membre-equipe/:id/edit',
        component: MembreEquipePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimrowadApp.membreEquipe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'membre-equipe/:id/delete',
        component: MembreEquipeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimrowadApp.membreEquipe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
