import { BaseEntity, User } from './../../shared';

export const enum ProjetStatus {
    'PENDING',
    'CANCELED',
    'VALIDATED'
}

export const enum Cible {
    'HOMME',
    'FEMME',
    'JEUNE'
}

export const enum TypeProjet {
    'SANTE',
    'TRANSPORT'
}

export class Projet implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public details?: string,
        public etudefContentType?: string,
        public etudef?: any,
        public etude2ContentType?: string,
        public etude2?: any,
        public etude3ContentType?: string,
        public etude3?: any,
        public rendement?: number,
        public budget?: number,
        public delai?: number,
        public date?: any,
        public status?: ProjetStatus,
        public cible?: Cible,
        public type?: TypeProjet,
        public equipe?: BaseEntity,
        public user?: User,
    ) {
    }
}
