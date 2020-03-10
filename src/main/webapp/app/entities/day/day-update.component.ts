import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDay, Day } from 'app/shared/model/day.model';
import { DayService } from './day.service';
import { IChannel } from 'app/shared/model/channel.model';
import { ChannelService } from 'app/entities/channel/channel.service';
import { ICalendar } from 'app/shared/model/calendar.model';
import { CalendarService } from 'app/entities/calendar/calendar.service';

type SelectableEntity = IChannel | ICalendar;

@Component({
  selector: 'jhi-day-update',
  templateUrl: './day-update.component.html'
})
export class DayUpdateComponent implements OnInit {
  isSaving = false;
  channels: IChannel[] = [];
  calendars: ICalendar[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    channels: [],
    calendars: []
  });

  constructor(
    protected dayService: DayService,
    protected channelService: ChannelService,
    protected calendarService: CalendarService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ day }) => {
      this.updateForm(day);

      this.channelService.query().subscribe((res: HttpResponse<IChannel[]>) => (this.channels = res.body || []));

      this.calendarService.query().subscribe((res: HttpResponse<ICalendar[]>) => (this.calendars = res.body || []));
    });
  }

  updateForm(day: IDay): void {
    this.editForm.patchValue({
      id: day.id,
      date: day.date,
      channels: day.channels,
      calendars: day.calendars
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const day = this.createFromForm();
    if (day.id !== undefined) {
      this.subscribeToSaveResponse(this.dayService.update(day));
    } else {
      this.subscribeToSaveResponse(this.dayService.create(day));
    }
  }

  private createFromForm(): IDay {
    return {
      ...new Day(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      channels: this.editForm.get(['channels'])!.value,
      calendars: this.editForm.get(['calendars'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDay>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
