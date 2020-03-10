import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChannel } from 'app/shared/model/channel.model';

type EntityResponseType = HttpResponse<IChannel>;
type EntityArrayResponseType = HttpResponse<IChannel[]>;

@Injectable({ providedIn: 'root' })
export class ChannelService {
  public resourceUrl = SERVER_API_URL + 'api/channels';

  constructor(protected http: HttpClient) {}

  create(channel: IChannel): Observable<EntityResponseType> {
    return this.http.post<IChannel>(this.resourceUrl, channel, { observe: 'response' });
  }

  update(channel: IChannel): Observable<EntityResponseType> {
    return this.http.put<IChannel>(this.resourceUrl, channel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChannel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
