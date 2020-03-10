import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICalendar, Calendar } from 'app/shared/model/calendar.model';
import { CalendarService } from './calendar.service';
import { ITransmition } from 'app/shared/model/transmition.model';
import { TransmitionService } from 'app/entities/transmition/transmition.service';

@Component({
  selector: 'jhi-calendar-update',
  templateUrl: './calendar-update.component.html'
})
export class CalendarUpdateComponent implements OnInit {
  isSaving = false;
  transmitions: ITransmition[] = [];

  editForm = this.fb.group({
    id: [],
    initialHour: [],
    finalHour: [],
    transmitions: []
  });

  constructor(
    protected calendarService: CalendarService,
    protected transmitionService: TransmitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendar }) => {
      this.updateForm(calendar);

      this.transmitionService.query().subscribe((res: HttpResponse<ITransmition[]>) => (this.transmitions = res.body || []));
    });
  }

  updateForm(calendar: ICalendar): void {
    this.editForm.patchValue({
      id: calendar.id,
      initialHour: calendar.initialHour,
      finalHour: calendar.finalHour,
      transmitions: calendar.transmitions
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calendar = this.createFromForm();
    if (calendar.id !== undefined) {
      this.subscribeToSaveResponse(this.calendarService.update(calendar));
    } else {
      this.subscribeToSaveResponse(this.calendarService.create(calendar));
    }
  }

  private createFromForm(): ICalendar {
    return {
      ...new Calendar(),
      id: this.editForm.get(['id'])!.value,
      initialHour: this.editForm.get(['initialHour'])!.value,
      finalHour: this.editForm.get(['finalHour'])!.value,
      transmitions: this.editForm.get(['transmitions'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalendar>>): void {
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

  trackById(index: number, item: ITransmition): any {
    return item.id;
  }
}
