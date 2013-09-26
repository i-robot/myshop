package com.enation.app.shop.core.plugin.goodsimp;

import org.w3c.dom.Document;

public abstract interface IBeforeGoodsImportEvent
{
  public abstract void onBeforeImport(Document paramDocument);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.plugin.goodsimp.IBeforeGoodsImportEvent
 * JD-Core Version:    0.6.1
 */