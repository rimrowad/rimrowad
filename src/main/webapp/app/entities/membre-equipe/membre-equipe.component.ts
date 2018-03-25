import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MembreEquipe } from './membre-equipe.model';
import { MembreEquipeService } from './membre-equipe.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-membre-equipe',
    templateUrl: './membre-equipe.component.html'
})
export class MembreEquipeComponent implements OnInit, OnDestroy {
membreEquipes: MembreEquipe[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private membreEquipeService: MembreEquipeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.membreEquipeService.query().subscribe(
            (res: HttpResponse<MembreEquipe[]>) => {
                this.membreEquipes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMembreEquipes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MembreEquipe) {
        return item.id;
    }
    registerChangeInMembreEquipes() {
        this.eventSubscriber = this.eventManager.subscribe('membreEquipeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
