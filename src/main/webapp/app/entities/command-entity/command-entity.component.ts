import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CommandEntity } from './command-entity.model';
import { CommandEntityService } from './command-entity.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-command-entity',
    templateUrl: './command-entity.component.html'
})
export class CommandEntityComponent implements OnInit, OnDestroy {
commandEntities: CommandEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private commandEntityService: CommandEntityService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.commandEntityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.commandEntities = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCommandEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CommandEntity) {
        return item.id;
    }
    registerChangeInCommandEntities() {
        this.eventSubscriber = this.eventManager.subscribe('commandEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
