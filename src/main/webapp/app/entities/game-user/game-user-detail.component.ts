import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GameUser } from './game-user.model';
import { GameUserService } from './game-user.service';

@Component({
    selector: 'jhi-game-user-detail',
    templateUrl: './game-user-detail.component.html'
})
export class GameUserDetailComponent implements OnInit, OnDestroy {

    gameUser: GameUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private gameUserService: GameUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGameUsers();
    }

    load(id) {
        this.gameUserService.find(id).subscribe((gameUser) => {
            this.gameUser = gameUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGameUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'gameUserListModification',
            (response) => this.load(this.gameUser.id)
        );
    }
}
