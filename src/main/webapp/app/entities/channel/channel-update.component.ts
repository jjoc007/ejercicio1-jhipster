import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChannel, Channel } from 'app/shared/model/channel.model';
import { ChannelService } from './channel.service';

@Component({
  selector: 'jhi-channel-update',
  templateUrl: './channel-update.component.html'
})
export class ChannelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: []
  });

  constructor(protected channelService: ChannelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ channel }) => {
      this.updateForm(channel);
    });
  }

  updateForm(channel: IChannel): void {
    this.editForm.patchValue({
      id: channel.id,
      type: channel.type
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const channel = this.createFromForm();
    if (channel.id !== undefined) {
      this.subscribeToSaveResponse(this.channelService.update(channel));
    } else {
      this.subscribeToSaveResponse(this.channelService.create(channel));
    }
  }

  private createFromForm(): IChannel {
    return {
      ...new Channel(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChannel>>): void {
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
