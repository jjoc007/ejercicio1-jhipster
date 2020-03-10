import { IChannel } from 'app/shared/model/channel.model';
import { ICalendar } from 'app/shared/model/calendar.model';

export interface IDay {
  id?: number;
  date?: string;
  channels?: IChannel;
  calendars?: ICalendar;
}

export class Day implements IDay {
  constructor(public id?: number, public date?: string, public channels?: IChannel, public calendars?: ICalendar) {}
}
