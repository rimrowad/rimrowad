/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { RimrowadTestModule } from '../../../test.module';
import { MembreEquipeDetailComponent } from '../../../../../../main/webapp/app/entities/membre-equipe/membre-equipe-detail.component';
import { MembreEquipeService } from '../../../../../../main/webapp/app/entities/membre-equipe/membre-equipe.service';
import { MembreEquipe } from '../../../../../../main/webapp/app/entities/membre-equipe/membre-equipe.model';

describe('Component Tests', () => {

    describe('MembreEquipe Management Detail Component', () => {
        let comp: MembreEquipeDetailComponent;
        let fixture: ComponentFixture<MembreEquipeDetailComponent>;
        let service: MembreEquipeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RimrowadTestModule],
                declarations: [MembreEquipeDetailComponent],
                providers: [
                    MembreEquipeService
                ]
            })
            .overrideTemplate(MembreEquipeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MembreEquipeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreEquipeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MembreEquipe(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.membreEquipe).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
