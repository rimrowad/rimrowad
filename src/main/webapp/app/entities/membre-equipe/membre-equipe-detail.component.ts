import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MembreEquipe } from './membre-equipe.model';
import { MembreEquipeService } from './membre-equipe.service';

@Component({
    selector: 'jhi-membre-equipe-detail',
    templateUrl: './membre-equipe-detail.component.html'
})
export class MembreEquipeDetailComponent implements OnInit, OnDestroy {

    membreEquipe: MembreEquipe;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private membreEquipeService: MembreEquipeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMembreEquipes();
    }

    load(id) {
        this.membreEquipeService.find(id)
            .subscribe((membreEquipeResponse: HttpResponse<MembreEquipe>) => {
                this.membreEquipe = membreEquipeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMembreEquipes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'membreEquipeListModification',
            (response) => this.load(this.membreEquipe.id)
        );
    }
}
