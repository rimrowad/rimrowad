import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Projet } from './projet.model';
import { ProjetService } from './projet.service';

@Component({
    selector: 'jhi-projet-detail',
    templateUrl: './projet-detail.component.html'
})
export class ProjetDetailComponent implements OnInit, OnDestroy {

    projet: Projet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private projetService: ProjetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProjets();
    }

    load(id) {
        this.projetService.find(id)
            .subscribe((projetResponse: HttpResponse<Projet>) => {
                this.projet = projetResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProjets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projetListModification',
            (response) => this.load(this.projet.id)
        );
    }
}
