import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { GameUser } from './game-user.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GameUserService {

    private resourceUrl = SERVER_API_URL + 'api/game-users';

    constructor(private http: Http) { }

    create(gameUser: GameUser): Observable<GameUser> {
        const copy = this.convert(gameUser);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(gameUser: GameUser): Observable<GameUser> {
        const copy = this.convert(gameUser);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<GameUser> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(gameUser: GameUser): GameUser {
        const copy: GameUser = Object.assign({}, gameUser);
        return copy;
    }
}
