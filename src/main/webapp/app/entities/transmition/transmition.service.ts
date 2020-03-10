import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransmition } from 'app/shared/model/transmition.model';

type EntityResponseType = HttpResponse<ITransmition>;
type EntityArrayResponseType = HttpResponse<ITransmition[]>;

@Injectable({ providedIn: 'root' })
export class TransmitionService {
  public resourceUrl = SERVER_API_URL + 'api/transmitions';

  constructor(protected http: HttpClient) {}

  create(transmition: ITransmition): Observable<EntityResponseType> {
    return this.http.post<ITransmition>(this.resourceUrl, transmition, { observe: 'response' });
  }

  update(transmition: ITransmition): Observable<EntityResponseType> {
    return this.http.put<ITransmition>(this.resourceUrl, transmition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransmition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransmition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
