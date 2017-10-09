/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GameUserDetailComponent } from '../../../../../../main/webapp/app/entities/game-user/game-user-detail.component';
import { GameUserService } from '../../../../../../main/webapp/app/entities/game-user/game-user.service';
import { GameUser } from '../../../../../../main/webapp/app/entities/game-user/game-user.model';

describe('Component Tests', () => {

    describe('GameUser Management Detail Component', () => {
        let comp: GameUserDetailComponent;
        let fixture: ComponentFixture<GameUserDetailComponent>;
        let service: GameUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [GameUserDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GameUserService,
                    JhiEventManager
                ]
            }).overrideTemplate(GameUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GameUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GameUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GameUser(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.gameUser).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
