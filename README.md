# split-net

A self-hosted WireGuard VPN control plane written in Java: manage sites (network segments), gateways, devices and users, and have the service configure a real WireGuard interface and generate peer configs for you.

## Architecture

The `domain` module is built as a **hexagonal / ports-and-adapters** application, organized by bounded concept rather than by technical layer:

- `domain/{device,gateway,group,site,user,wireguard,globalConfig,accessControlRule}/` — pure domain model, use cases and outbound **ports** (interfaces) per concept (e.g. `CreateDevicePort`, `FindGatewayPort`, `SaveSitePort`).
- `application/inbound/rest/` — REST controllers (`DeviceController`, `GatewayController`, `GroupController`, `SiteController`, `UserController`, `GatewayConfigController`) that drive the use cases.
- `application/outbound/db/` — JPA adapters implementing the domain ports (`FindDeviceAdapter`, `SaveGatewayAdapter`, ...).
- `infrastructure/` — the adapters that talk to the outside world: `wg/WireguardCliAdapter` and `nft/NftableCliAdapter` shell out to the real `wg` and `nft` (nftables) command-line tools, `security/JwtUtils` handles token auth on top of Spring Security's **OAuth2 Authorization Server**.

On startup, `WireGuardInitializer` generates (or reuses) a WireGuard private key, brings up a real `wg0` network interface with `ip link` / `wg set`, and re-registers all known device peers — this is a genuine network-configuration service, not just a CRUD app over a WireGuard-shaped schema. Sites use CIDR math (`CidrUtils`) to validate subnets, and `DeviceConfigGenerator` produces ready-to-use client WireGuard config files.

The repo also contains a second module, **`gateway`** — a lightweight remote agent (currently an early scaffold) meant to run on a site's actual gateway node and expose the same `wg`/CLI adapters locally, so the `domain` control plane can eventually manage WireGuard on remote sites rather than only on its own host. It isn't part of the local docker-compose setup below — it's designed to be deployed on real gateway hardware with host networking, not sandboxed.

## Tech stack

- Java 17, Spring Boot
- Spring Security + Spring Authorization Server (OAuth2)
- Spring Data JPA + PostgreSQL
- Native `wg` (wireguard-tools) and `nft` (nftables) CLI integration via `ProcessBuilder`
- Maven

## Running locally

`docker-compose.yml`, `Dockerfile` and an `.env.example` (copy to `.env`) live in the `domain/` module. Because the app manages a real WireGuard network interface, the container needs elevated network privileges — this can't run fully sandboxed:

```bash
cd domain
cp .env.example .env
docker compose up --build
```

This starts the `app` service (needs `NET_ADMIN` capability and `/dev/net/tun` passthrough, both already wired in the compose file) plus a `postgres` instance. The app exposes its HTTP API on `${APP_PORT}` and the WireGuard UDP port on `${WG_PORT}`.
