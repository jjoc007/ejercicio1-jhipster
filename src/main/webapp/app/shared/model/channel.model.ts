import { IDay } from 'app/shared/model/day.model';

export interface IChannel {
  id?: number;
  type?: string;
  days?: IDay[];
}

export class Channel implements IChannel {
  constructor(public id?: number, public type?: string, public days?: IDay[]) {}
}
