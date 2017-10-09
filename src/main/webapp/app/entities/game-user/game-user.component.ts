import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { GameUser } from './game-user.model';
import { GameUserService } from './game-user.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-game-user',
    templateUrl: './game-user.component.html'
})
export class GameUserComponent implements OnInit, OnDestroy {
gameUsers: GameUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private gameUserService: GameUserService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.gameUserService.query().subscribe(
            (res: ResponseWrapper) => {
                this.gameUsers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGameUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: GameUser) {
        return item.id;
    }
    registerChangeInGameUsers() {
        this.eventSubscriber = this.eventManager.subscribe('gameUserListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
