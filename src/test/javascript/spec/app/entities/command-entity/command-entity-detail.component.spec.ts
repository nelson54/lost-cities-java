/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CommandEntityDetailComponent } from '../../../../../../main/webapp/app/entities/command-entity/command-entity-detail.component';
import { CommandEntityService } from '../../../../../../main/webapp/app/entities/command-entity/command-entity.service';
import { CommandEntity } from '../../../../../../main/webapp/app/entities/command-entity/command-entity.model';

describe('Component Tests', () => {

    describe('CommandEntity Management Detail Component', () => {
        let comp: CommandEntityDetailComponent;
        let fixture: ComponentFixture<CommandEntityDetailComponent>;
        let service: CommandEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [CommandEntityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CommandEntityService,
                    JhiEventManager
                ]
            }).overrideTemplate(CommandEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommandEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommandEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CommandEntity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.commandEntity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
