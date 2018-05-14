package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.CompositeFuture;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.jdbc.JDBCClient;

public class MainVerticle extends AbstractVerticle {

  private JDBCClient dbClient;

  private Future<Void> prepareDatabase() {
    Future <Void> future = Future.future();
    // todo;
    return future;
  }

  private Future<Void> startHttpServer() {
    Future <Void> future = Future.future();
    
    HttpServer server = vertx.createHttpServer();

    Router router = Router.router(vertx);
    router.get("/").handler(this::indexHandler);
/*
    router.get("/wiki/:page").handler(this.pageRenderingHandler);
    router.post().handler(BodyHandler.create());
    router.post("/save").handler(this::pageUpdateHandler)
    router.post("/create").handler(this::pageCreateHandler);
    router.post("/delete").handler(this.pageDeletionHandler);
*/
    server
    .requestHandler(router::accept)
    .listen(9000, ar -> {
      if (ar.succeeded()) {
        future.complete();
      } else {
        future.fail(ar.cause());
      }
    });
    
    return future;
  }

  private void indexHandler(RoutingContext context) {
    //todo
  }

  @Override
  public void start(Future<Void> startFuture) {
    /*
    vertx.createHttpServer()
        .requestHandler(req -> req.response().end("Hello Vert.x!@#$%!"))
        .listen(9000);
    */
    Future <Void> steps = prepareDatabase().compose(v -> startHttpServer());
    steps.setHandler(ar -> {
      if (ar.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(ar.cause());
      }
    });
  }
}
