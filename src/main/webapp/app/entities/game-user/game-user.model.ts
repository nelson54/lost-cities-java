import { BaseEntity, User } from './../../shared';

export class GameUser implements BaseEntity {
    constructor(
        public id?: number,
        public user?: User,
        public commandEntity?: BaseEntity,
        public match?: BaseEntity,
    ) {
    }
}
