/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RimrowadTestModule } from '../../../test.module';
import { MembreEquipeDialogComponent } from '../../../../../../main/webapp/app/entities/membre-equipe/membre-equipe-dialog.component';
import { MembreEquipeService } from '../../../../../../main/webapp/app/entities/membre-equipe/membre-equipe.service';
import { MembreEquipe } from '../../../../../../main/webapp/app/entities/membre-equipe/membre-equipe.model';
import { EquipeService } from '../../../../../../main/webapp/app/entities/equipe';

describe('Component Tests', () => {

    describe('MembreEquipe Management Dialog Component', () => {
        let comp: MembreEquipeDialogComponent;
        let fixture: ComponentFixture<MembreEquipeDialogComponent>;
        let service: MembreEquipeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RimrowadTestModule],
                declarations: [MembreEquipeDialogComponent],
                providers: [
                    EquipeService,
                    MembreEquipeService
                ]
            })
            .overrideTemplate(MembreEquipeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MembreEquipeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreEquipeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MembreEquipe(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.membreEquipe = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'membreEquipeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MembreEquipe();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.membreEquipe = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'membreEquipeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
