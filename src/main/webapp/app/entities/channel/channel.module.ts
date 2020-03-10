import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BroadcastSharedModule } from 'app/shared/shared.module';
import { ChannelComponent } from './channel.component';
import { ChannelDetailComponent } from './channel-detail.component';
import { ChannelUpdateComponent } from './channel-update.component';
import { ChannelDeleteDialogComponent } from './channel-delete-dialog.component';
import { channelRoute } from './channel.route';

@NgModule({
  imports: [BroadcastSharedModule, RouterModule.forChild(channelRoute)],
  declarations: [ChannelComponent, ChannelDetailComponent, ChannelUpdateComponent, ChannelDeleteDialogComponent],
  entryComponents: [ChannelDeleteDialogComponent]
})
export class BroadcastChannelModule {}
