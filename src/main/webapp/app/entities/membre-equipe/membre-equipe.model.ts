import { BaseEntity } from './../../shared';

export class MembreEquipe implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public age?: number,
        public diplome?: string,
        public experience?: string,
        public equipe?: BaseEntity,
    ) {
    }
}
