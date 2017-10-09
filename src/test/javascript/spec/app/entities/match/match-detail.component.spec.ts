/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MatchDetailComponent } from '../../../../../../main/webapp/app/entities/match/match-detail.component';
import { MatchService } from '../../../../../../main/webapp/app/entities/match/match.service';
import { Match } from '../../../../../../main/webapp/app/entities/match/match.model';

describe('Component Tests', () => {

    describe('Match Management Detail Component', () => {
        let comp: MatchDetailComponent;
        let fixture: ComponentFixture<MatchDetailComponent>;
        let service: MatchService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [MatchDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MatchService,
                    JhiEventManager
                ]
            }).overrideTemplate(MatchDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Match(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.match).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
