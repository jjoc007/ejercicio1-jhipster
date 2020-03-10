import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChannel } from 'app/shared/model/channel.model';
import { ChannelService } from './channel.service';

@Component({
  templateUrl: './channel-delete-dialog.component.html'
})
export class ChannelDeleteDialogComponent {
  channel?: IChannel;

  constructor(protected channelService: ChannelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.channelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('channelListModification');
      this.activeModal.close();
    });
  }
}
