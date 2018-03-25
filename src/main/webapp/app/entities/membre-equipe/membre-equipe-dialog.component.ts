import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MembreEquipe } from './membre-equipe.model';
import { MembreEquipePopupService } from './membre-equipe-popup.service';
import { MembreEquipeService } from './membre-equipe.service';
import { Equipe, EquipeService } from '../equipe';

@Component({
    selector: 'jhi-membre-equipe-dialog',
    templateUrl: './membre-equipe-dialog.component.html'
})
export class MembreEquipeDialogComponent implements OnInit {

    membreEquipe: MembreEquipe;
    isSaving: boolean;

    equipes: Equipe[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private membreEquipeService: MembreEquipeService,
        private equipeService: EquipeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.equipeService.query()
            .subscribe((res: HttpResponse<Equipe[]>) => { this.equipes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.membreEquipe.id !== undefined) {
            this.subscribeToSaveResponse(
                this.membreEquipeService.update(this.membreEquipe));
        } else {
            this.subscribeToSaveResponse(
                this.membreEquipeService.create(this.membreEquipe));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MembreEquipe>>) {
        result.subscribe((res: HttpResponse<MembreEquipe>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MembreEquipe) {
        this.eventManager.broadcast({ name: 'membreEquipeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEquipeById(index: number, item: Equipe) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-membre-equipe-popup',
    template: ''
})
export class MembreEquipePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private membreEquipePopupService: MembreEquipePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.membreEquipePopupService
                    .open(MembreEquipeDialogComponent as Component, params['id']);
            } else {
                this.membreEquipePopupService
                    .open(MembreEquipeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
