import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommandEntity } from './command-entity.model';
import { CommandEntityPopupService } from './command-entity-popup.service';
import { CommandEntityService } from './command-entity.service';

@Component({
    selector: 'jhi-command-entity-delete-dialog',
    templateUrl: './command-entity-delete-dialog.component.html'
})
export class CommandEntityDeleteDialogComponent {

    commandEntity: CommandEntity;

    constructor(
        private commandEntityService: CommandEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commandEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'commandEntityListModification',
                content: 'Deleted an commandEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-command-entity-delete-popup',
    template: ''
})
export class CommandEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commandEntityPopupService: CommandEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.commandEntityPopupService
                .open(CommandEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
