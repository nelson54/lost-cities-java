import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CommandEntity } from './command-entity.model';
import { CommandEntityPopupService } from './command-entity-popup.service';
import { CommandEntityService } from './command-entity.service';
import { GameUser, GameUserService } from '../game-user';
import { Match, MatchService } from '../match';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-command-entity-dialog',
    templateUrl: './command-entity-dialog.component.html'
})
export class CommandEntityDialogComponent implements OnInit {

    commandEntity: CommandEntity;
    isSaving: boolean;

    users: GameUser[];

    matches: Match[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private commandEntityService: CommandEntityService,
        private gameUserService: GameUserService,
        private matchService: MatchService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.gameUserService
            .query({filter: 'commandentity-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.commandEntity.user || !this.commandEntity.user.id) {
                    this.users = res.json;
                } else {
                    this.gameUserService
                        .find(this.commandEntity.user.id)
                        .subscribe((subRes: GameUser) => {
                            this.users = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.matchService.query()
            .subscribe((res: ResponseWrapper) => { this.matches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commandEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commandEntityService.update(this.commandEntity));
        } else {
            this.subscribeToSaveResponse(
                this.commandEntityService.create(this.commandEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<CommandEntity>) {
        result.subscribe((res: CommandEntity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CommandEntity) {
        this.eventManager.broadcast({ name: 'commandEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackGameUserById(index: number, item: GameUser) {
        return item.id;
    }

    trackMatchById(index: number, item: Match) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-command-entity-popup',
    template: ''
})
export class CommandEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commandEntityPopupService: CommandEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commandEntityPopupService
                    .open(CommandEntityDialogComponent as Component, params['id']);
            } else {
                this.commandEntityPopupService
                    .open(CommandEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
