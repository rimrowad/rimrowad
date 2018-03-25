import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MembreEquipe } from './membre-equipe.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MembreEquipe>;

@Injectable()
export class MembreEquipeService {

    private resourceUrl =  SERVER_API_URL + 'api/membre-equipes';

    constructor(private http: HttpClient) { }

    create(membreEquipe: MembreEquipe): Observable<EntityResponseType> {
        const copy = this.convert(membreEquipe);
        return this.http.post<MembreEquipe>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(membreEquipe: MembreEquipe): Observable<EntityResponseType> {
        const copy = this.convert(membreEquipe);
        return this.http.put<MembreEquipe>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MembreEquipe>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MembreEquipe[]>> {
        const options = createRequestOption(req);
        return this.http.get<MembreEquipe[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MembreEquipe[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MembreEquipe = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MembreEquipe[]>): HttpResponse<MembreEquipe[]> {
        const jsonResponse: MembreEquipe[] = res.body;
        const body: MembreEquipe[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MembreEquipe.
     */
    private convertItemFromServer(membreEquipe: MembreEquipe): MembreEquipe {
        const copy: MembreEquipe = Object.assign({}, membreEquipe);
        return copy;
    }

    /**
     * Convert a MembreEquipe to a JSON which can be sent to the server.
     */
    private convert(membreEquipe: MembreEquipe): MembreEquipe {
        const copy: MembreEquipe = Object.assign({}, membreEquipe);
        return copy;
    }
}
