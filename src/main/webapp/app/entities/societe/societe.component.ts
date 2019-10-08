import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISociete } from 'app/shared/model/societe.model';
import { AccountService } from 'app/core';
import { SocieteService } from './societe.service';

@Component({
  selector: 'jhi-societe',
  templateUrl: './societe.component.html'
})
export class SocieteComponent implements OnInit, OnDestroy {
  societes: ISociete[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected societeService: SocieteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.societeService
      .query()
      .pipe(
        filter((res: HttpResponse<ISociete[]>) => res.ok),
        map((res: HttpResponse<ISociete[]>) => res.body)
      )
      .subscribe(
        (res: ISociete[]) => {
          this.societes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSocietes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISociete) {
    return item.id;
  }

  registerChangeInSocietes() {
    this.eventSubscriber = this.eventManager.subscribe('societeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
