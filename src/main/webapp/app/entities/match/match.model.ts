import { BaseEntity } from './../../shared';

export class Match implements BaseEntity {
    constructor(
        public id?: number,
        public initialSeed?: number,
        public gameUsers?: BaseEntity[],
        public commands?: BaseEntity[],
    ) {
    }
}
