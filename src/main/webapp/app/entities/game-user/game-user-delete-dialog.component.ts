import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GameUser } from './game-user.model';
import { GameUserPopupService } from './game-user-popup.service';
import { GameUserService } from './game-user.service';

@Component({
    selector: 'jhi-game-user-delete-dialog',
    templateUrl: './game-user-delete-dialog.component.html'
})
export class GameUserDeleteDialogComponent {

    gameUser: GameUser;

    constructor(
        private gameUserService: GameUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gameUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'gameUserListModification',
                content: 'Deleted an gameUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-game-user-delete-popup',
    template: ''
})
export class GameUserDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gameUserPopupService: GameUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.gameUserPopupService
                .open(GameUserDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
