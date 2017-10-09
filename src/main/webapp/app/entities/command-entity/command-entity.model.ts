import { BaseEntity } from './../../shared';

export class CommandEntity implements BaseEntity {
    constructor(
        public id?: number,
        public color?: string,
        public play?: string,
        public discard?: string,
        public user?: BaseEntity,
        public match?: BaseEntity,
    ) {
    }
}
