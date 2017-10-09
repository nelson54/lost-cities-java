import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CommandEntity } from './command-entity.model';
import { CommandEntityService } from './command-entity.service';

@Component({
    selector: 'jhi-command-entity-detail',
    templateUrl: './command-entity-detail.component.html'
})
export class CommandEntityDetailComponent implements OnInit, OnDestroy {

    commandEntity: CommandEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private commandEntityService: CommandEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommandEntities();
    }

    load(id) {
        this.commandEntityService.find(id).subscribe((commandEntity) => {
            this.commandEntity = commandEntity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommandEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commandEntityListModification',
            (response) => this.load(this.commandEntity.id)
        );
    }
}
