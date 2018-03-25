/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RimrowadTestModule } from '../../../test.module';
import { MembreEquipeComponent } from '../../../../../../main/webapp/app/entities/membre-equipe/membre-equipe.component';
import { MembreEquipeService } from '../../../../../../main/webapp/app/entities/membre-equipe/membre-equipe.service';
import { MembreEquipe } from '../../../../../../main/webapp/app/entities/membre-equipe/membre-equipe.model';

describe('Component Tests', () => {

    describe('MembreEquipe Management Component', () => {
        let comp: MembreEquipeComponent;
        let fixture: ComponentFixture<MembreEquipeComponent>;
        let service: MembreEquipeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RimrowadTestModule],
                declarations: [MembreEquipeComponent],
                providers: [
                    MembreEquipeService
                ]
            })
            .overrideTemplate(MembreEquipeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MembreEquipeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreEquipeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MembreEquipe(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.membreEquipes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
