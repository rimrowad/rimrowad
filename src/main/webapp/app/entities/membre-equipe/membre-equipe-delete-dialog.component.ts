import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MembreEquipe } from './membre-equipe.model';
import { MembreEquipePopupService } from './membre-equipe-popup.service';
import { MembreEquipeService } from './membre-equipe.service';

@Component({
    selector: 'jhi-membre-equipe-delete-dialog',
    templateUrl: './membre-equipe-delete-dialog.component.html'
})
export class MembreEquipeDeleteDialogComponent {

    membreEquipe: MembreEquipe;

    constructor(
        private membreEquipeService: MembreEquipeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.membreEquipeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'membreEquipeListModification',
                content: 'Deleted an membreEquipe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-membre-equipe-delete-popup',
    template: ''
})
export class MembreEquipeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private membreEquipePopupService: MembreEquipePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.membreEquipePopupService
                .open(MembreEquipeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
