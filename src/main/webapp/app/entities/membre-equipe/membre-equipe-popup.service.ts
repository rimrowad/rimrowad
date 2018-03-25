import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MembreEquipe } from './membre-equipe.model';
import { MembreEquipeService } from './membre-equipe.service';

@Injectable()
export class MembreEquipePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private membreEquipeService: MembreEquipeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.membreEquipeService.find(id)
                    .subscribe((membreEquipeResponse: HttpResponse<MembreEquipe>) => {
                        const membreEquipe: MembreEquipe = membreEquipeResponse.body;
                        this.ngbModalRef = this.membreEquipeModalRef(component, membreEquipe);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.membreEquipeModalRef(component, new MembreEquipe());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    membreEquipeModalRef(component: Component, membreEquipe: MembreEquipe): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.membreEquipe = membreEquipe;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
