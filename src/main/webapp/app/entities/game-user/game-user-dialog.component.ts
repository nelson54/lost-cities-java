import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GameUser } from './game-user.model';
import { GameUserPopupService } from './game-user-popup.service';
import { GameUserService } from './game-user.service';
import { User, UserService } from '../../shared';
import { CommandEntity, CommandEntityService } from '../command-entity';
import { Match, MatchService } from '../match';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-game-user-dialog',
    templateUrl: './game-user-dialog.component.html'
})
export class GameUserDialogComponent implements OnInit {

    gameUser: GameUser;
    isSaving: boolean;

    users: User[];

    commandentities: CommandEntity[];

    matches: Match[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private gameUserService: GameUserService,
        private userService: UserService,
        private commandEntityService: CommandEntityService,
        private matchService: MatchService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.commandEntityService.query()
            .subscribe((res: ResponseWrapper) => { this.commandentities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.matchService.query()
            .subscribe((res: ResponseWrapper) => { this.matches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.gameUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.gameUserService.update(this.gameUser));
        } else {
            this.subscribeToSaveResponse(
                this.gameUserService.create(this.gameUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<GameUser>) {
        result.subscribe((res: GameUser) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GameUser) {
        this.eventManager.broadcast({ name: 'gameUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCommandEntityById(index: number, item: CommandEntity) {
        return item.id;
    }

    trackMatchById(index: number, item: Match) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-game-user-popup',
    template: ''
})
export class GameUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gameUserPopupService: GameUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.gameUserPopupService
                    .open(GameUserDialogComponent as Component, params['id']);
            } else {
                this.gameUserPopupService
                    .open(GameUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
