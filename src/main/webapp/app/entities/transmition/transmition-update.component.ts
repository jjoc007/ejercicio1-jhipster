import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransmition, Transmition } from 'app/shared/model/transmition.model';
import { TransmitionService } from './transmition.service';

@Component({
  selector: 'jhi-transmition-update',
  templateUrl: './transmition-update.component.html'
})
export class TransmitionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected transmitionService: TransmitionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transmition }) => {
      this.updateForm(transmition);
    });
  }

  updateForm(transmition: ITransmition): void {
    this.editForm.patchValue({
      id: transmition.id,
      name: transmition.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transmition = this.createFromForm();
    if (transmition.id !== undefined) {
      this.subscribeToSaveResponse(this.transmitionService.update(transmition));
    } else {
      this.subscribeToSaveResponse(this.transmitionService.create(transmition));
    }
  }

  private createFromForm(): ITransmition {
    return {
      ...new Transmition(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransmition>>): void {
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
}
