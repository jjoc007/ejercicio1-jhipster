import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDay } from 'app/shared/model/day.model';

type EntityResponseType = HttpResponse<IDay>;
type EntityArrayResponseType = HttpResponse<IDay[]>;

@Injectable({ providedIn: 'root' })
export class DayService {
  public resourceUrl = SERVER_API_URL + 'api/days';

  constructor(protected http: HttpClient) {}

  create(day: IDay): Observable<EntityResponseType> {
    return this.http.post<IDay>(this.resourceUrl, day, { observe: 'response' });
  }

  update(day: IDay): Observable<EntityResponseType> {
    return this.http.put<IDay>(this.resourceUrl, day, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDay>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDay[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
